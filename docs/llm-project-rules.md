# Presencify KMP — Project Rules for LLM

These rules describe how to structure, name, and implement screens in Presencify, including role-based visibility, snackbar, dialogs, search screens, navigation, and error handling.

## Directory Structure
- **App Host:** [Presencify/composeApp/src/commonMain/kotlin/edu/watumull/presencify/App.kt](Presencify/composeApp/src/commonMain/kotlin/edu/watumull/presencify/App.kt) provides `LocalUserRole` and collects global snackbar.
- **Design System:** [Presencify/core/design-systems/src/commonMain/kotlin/edu/watumull/presencify/core/design/systems/components](Presencify/core/design-systems/src/commonMain/kotlin/edu/watumull/presencify/core/design/systems/components) offers `PresencifyScaffold`, `PresencifyBottomSheetScaffold`, dialogs, `PresencifySearchBar`, buttons, text fields.
- **Presentation Utilities:** [Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation) contains MVI helpers, error-to-UI mapping, templates, and snackbar.
- **Feature Modules:** Place screens under `Presencify/feature/<module>/src/commonMain/kotlin/edu/watumull/presencify/feature/<domain>/<screen>` (modules can host multiple screens, e.g., `feature/users` → `SearchStudent`, `AddEditStudent`, `SearchTeacher`).
 - **Shared Composables:** Put basic cross-feature components in design-systems. If a component depends on domain models or presentation utilities, place it under `core/presentation/components`. Implement a screen-specific shimmer for each screen rather than a global one.
 - **Module Dependencies:** Feature modules must not depend on other feature modules. Composition wiring happens in `composeApp`.
 - **Koin Aggregation:** Each feature declares a top-level `val` Koin `Module` (e.g., `val usersModule = module { viewModel { ... } }`). In `composeApp`, aggregate feature modules via Koin’s `includes()`.
 - **Feature Boundaries:** Features contain presentation/UI only. DTOs and mappers live strictly in `:core:data` and `:core:domain` — no cross-feature data types.

## Screen Architecture (MVI)
- **Files per screen:** `NameRoot`, `NameScreen`, `NameViewModel`, `NameState`, `NameEvent`, `NameAction` (+ `NameBottomSheetContent` for search screens). Use templates:
  - [PresencifyScreenTemplate.txt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/files_templates/PresencifyScreenTemplate.txt)
  - [PresencifySearchScreenTemplate.txt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/files_templates/PresencifySearchScreenTemplate.txt)
 - **Root:** Inject VM via Koin (`koinViewModel()`), observe one-shot `Event`s with `EventsEffect`. Handle intra-feature navigation using the feature’s own `NavController` (via that feature’s `NavGraphBuilder` extension). For cross-feature navigation, `composeApp` passes lambdas into `Root` (e.g., `onNavigateToLogin()`, `onNavigateToStudent(id)`), and `Root` calls those lambdas when handling events. Every screen has a `Root`.
- **Screen:** Render `state.viewState` (Loading/Error/Content) inside scaffold; forward user intents to `onAction`.
 - **ViewModel:** Extend `BaseViewModel<State, Event, Action>` (see [BaseViewModel.kt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/utils/BaseViewModel.kt)); keep `Action`s (user intents) separate from `Event`s (one-shots like navigation/snackbar); update state synchronously; do async work in `viewModelScope.launch` and emit follow-up actions/events.
 - **Concurrency:** Prefer cancellable operations (e.g., cancel previous search on query change) and use structured concurrency patterns (e.g., `SupervisorJob`) for grouped work.
 - **Event Handling:** `EventsEffect` collects only while the screen is STARTED to prevent duplicate handling; use it for navigation.
 - **Loading UI:** Prefer shimmer placeholders for loading content; avoid modal loading dialogs.
 - **Search Flow:** For search-like flows, use `debounce(300ms)` and `collectLatest` to cancel in-flight work.
 - **Navigation Extensions:** Each feature defines its own `NavGraphBuilder` extension for routes and uses `composableWithSlideTransitions` for consistent enter/exit transitions.

## Role & Visibility
- **CompositionLocal:** Access role via `LocalUserRole.current` from [App.kt](Presencify/composeApp/src/commonMain/kotlin/edu/watumull/presencify/App.kt).
- **Roles:** `ADMIN`, `TEACHER`, `STUDENT` (see [UserRole.kt](Presencify/core/domain/src/commonMain/kotlin/edu/watumull/presencify/core/domain/model/auth/UserRole.kt)).
- **Visibility:** Gate sensitive admin-only UI by checking `LocalUserRole.current == UserRole.ADMIN` at render time. Other role-specific gating will be instructed per feature.
 - **Hidden Actions:** Hide entire UI branches for non-admins; do not disable with tooltips.
 - **Role Changes:** Role changes re-gate UI; no explicit reload needed as login flows handle role updates.

## Feedback & Errors
- **Global Snackbar:** On successful server operations, call `SnackbarController.sendEvent(SnackbarEvent(message, action?))` from VM (wrap in `viewModelScope.launch` as it’s `suspend`). See [SnackbarController.kt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/global_snackbar/SnackbarController.kt).
  - Use context-appropriate messages (e.g., "Login successful", "Student added successfully").
  - Omit snackbar actions unless clearly useful; for interactions like undo/confirm/retry prefer dialogs.
  - Do not show snackbars for routine successes like list fetches on search screens.
  - Placement: Use only the global host from `App.kt`; do not create local snackbar hosts inside screens.
- **Dialogs:** Use `PresencifyAlertDialog` for errors, info, and confirmations via `state.dialogState`.
  - Confirmations for risky operations (e.g., delete) must use `DialogType.CONFIRM_RISKY_ACTION`.
  - Server-sent business messages (`DataError.Remote.BusinessLogicError`) should show as `DialogType.ERROR` with the server-provided message.
  - Dialog types are defined in [DialogType.kt](Presencify/core/design-systems/src/commonMain/kotlin/edu/watumull/presencify/core/design/systems/components/dialog/DialogType.kt).
   - Dialog stacking: allow only one visible at a time; queue subsequent dialogs and show them after the current one is dismissed.
   - Delete confirmations: include the entity name when available ("Are you sure you want to delete <name>?"); if not available, use a generic message ("Are you sure you want to delete this?").
  - Rate Limiting: On `TooManyRequests`, show an INFO dialog. Do not auto-retry; user may retry later.
  - Success Feedback: Do not use dialogs to confirm success; use the global snackbar only to tell it's a success.
 - **Unauthorized/Forbidden:** Unauthorized is handled via token refresh; Forbidden should show an error dialog (ideally prevented by role gating).
   - For Forbidden, show `DialogType.ERROR` using the message from `DataError.toUiText()` and keep the user on the current screen.
 - **Result Handling:** Always use the `onSuccess { }` / `onError { }` chaining pattern from [Result.kt](Presencify/core/domain/src/commonMain/kotlin/edu/watumull/presencify/core/domain/Result.kt); avoid `when (result)` style in ViewModels.
 - **Retry Policy:** Do not auto-retry; retries should be user-driven via dialogs.

## Search Screens
- **Template:** Follow [PresencifySearchScreenTemplate.txt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/files_templates/PresencifySearchScreenTemplate.txt).
- **Conventions:** Use `PresencifySearchBar` (`UpdateSearchQuery`, `Search`, `ShowBottomSheet`), bottom sheet filters via `BottomSheetState.selectedOptions`, and pull-to-refresh via `Refresh`/`isRefreshing`.
 - **Empty/Error States:** Standardize on `PresencifyNoResultsIndicator` for both empty results and error states; do not use snackbar for these states.
 - **Pagination:** Use `LazyColumn` with the helper in `core/presentation/pagination`.
 - **Debounce:** Debounce search queries by 300ms before firing requests.
 - **Filters:** Persist filter selections per module and restore them on revisit, this will happen via ViewModel automatically.
  - **Paging Parameters:** Standardize `pageSize = 20` and `prefetchThreshold = 20` across search lists.
  - **Default Sort:** When the server doesn’t specify, sort by name ascending.
    - Never Apply client-side sorting when the repository/server do not support sort parameters.
  - **End of List:** When all pages are loaded, stop loading silently; do not show a footer.
  - **Paging Error:** If a page load fails mid-scroll, show an error dialog.
  - **Retry:** Do not auto-retry or retry on scroll; let user manually retry via pull-to-refresh only.
 - **Search Submit:** Trigger search on IME action "Search" and on the explicit search button; both dispatch the same `Action`.
 - **Persistence:** Persist filters only in the ViewModel state (do not store in datastore across restarts).
 - **Initial Load:** On first load, fetch with an empty query to populate initial results.

## Navigation
 - **Events:** ViewModel emits navigation events (`NavigateTo<Screen>(args)`, `NavigateBack`).
  - **Intra-feature navigation:** `Root` uses its feature-specific `NavController` and `NavGraphBuilder` extension to navigate internal routes.
  - **Cross-feature navigation:** `composeApp` provides lambdas to `Root` to perform app-level navigation. `Root` invokes these lambdas when handling events. Features must not import other feature routes or `NavController`s.
 - **Args Passing:** Pass IDs only for simplicity; avoid passing DTOs.
 - **Submit Policy:** After successful create/update operation on speicifc screen, show a global snackbar and navigate to there details screen (keep the create/update screen in the backstack as the user might navigateUp to make edit).
 - **Deep Links:** Future work; no current constraints.
 - **Event Naming (multi-arg):** Prefer `NavigateTo<Screen>(id, tab?)` for multi-argument navigation (avoid opaque parameter objects).
 - **Unsaved Changes:** If edits are present and the user navigates back, show a confirmation dialog (`DialogType.CONFIRM_NORMAL_ACTION`).

## Networking & Error Mapping
- **Result Type:** Data/domain operations return `Result<D, DataError.Remote>` (see [Result.kt](Presencify/core/domain/src/commonMain/kotlin/edu/watumull/presencify/core/domain/Result.kt)).
  - **Error to UI:** Map errors to user-facing text via `DataError.toUiText()` (see [DataErrorToUiText.kt](Presencify/core/presentation/src/commonMain/kotlin/edu/watumull/presencify/core/presentation/DataErrorToUiText.kt)).
 - **Business Logic Errors:** Display server-provided business messages using `DialogType.ERROR`.
 - **Offline Mode:** On `NoInternet`, show a dialog; no offline caching planned.

## Naming & Packaging
- **Packages:** Use `edu.watumull.presencify.feature.<domain>.<screen>` inside the feature module. Modules can host multiple screens (e.g., `feature/users` with `SearchStudent`, `AddEditStudent`, `SearchTeacher`).
 - **Koin Modules:** Each feature exposes a single Koin `Module` that declares all of its `viewModel { ... }` bindings.
  - **DI Scope:** Features may bind lightweight helpers (formatters/mappers) if needed, but most bindings will be `ViewModel`s.
 - **Routes:** Use PascalCase route IDs (e.g., `StudentProfile`, `SearchStudent`).

## Design System & UX
- **Width Constraints:** Outer containers fill max size; inner columns/cards/etc. should use `UiConstants.MAX_CONTENT_WIDTH`.
- **Loading Indicators:** Prefer shimmer effects for content loading; avoid modal loading dialogs.
- **Empty States:** Use `PresencifyNoResultsIndicator` with custom messages.
- **Accessibility:** Add short, clear content descriptions for tappables and images.
 - **Validation UX:** Validate on each keystroke using validators in `core/presentation/validation`, show inline field errors, and block submission until all inputs are valid (return early on submit if invalid).
 - **Typography:** Use `MaterialTheme.typography` (from design-systems). Standardize: `titleLarge` for screen titles, `titleMedium` for section headers, and `bodyLarge/bodyMedium` for content text.
  - Submit buttons remain enabled; on click, validate and return early while showing inline errors.
 - **Required Markers:** Indicate required fields with an asterisk by building annotated text and appending a red `*`.
 - **IME Actions:** Standardize IME actions per field (`Next`, `Done`, `Search`) for consistent form flows.
 - **Lists:** Standardize item spacing at 12dp across the app. Prefer grouped spacing over dividers unless necessary.
 - **Buttons & Inputs:** Stritcly use components from `PresencifyButtons.kt`, `PresencifyTextField.kt` instead of normal Buttons, TextFileds, and use design-system cards.
 - **List Rows:** Prefer `ListItem` or cards with ripple for entity items.

## Performance & Caching
- **Images:** Use Coil for image loading/caching.
 - **Placeholders:** Use `baseline_account_circle_24` from resources for avatar/profile placeholders and as a default error image where applicable.
  - **Non-avatar Images:** Use the same baseline icon as a fallback for feature-specific images (e.g., course icons).
 - **Recomposition Control:** Use `remember` and `derivedStateOf` for expensive computations.
 - **Image Sizes:** Standardize avatar/thumbnail sizes at ~48dp to avoid layout jumps.
 - **LazyColumn Keys:** Provide stable `key` for items to avoid unnecessary recompositions on stable lists.

## Testing Guidance
- **ViewModel Tests:** Verify action→state transitions (loading, success, error), dialog states, and emitted events.
- **UI Tests (JVM-only):** Target Compose UI tests on JVM. Verify user behavior on screen, role-gated visibility, snackbar emission on success, dialog behavior for risky actions and info messages, and search screen interactions (query, filters, refresh).
 - **Coverage:** Aim for ≥80% coverage per feature module; prefer behavior tests over snapshot tests.
 - **Coroutines:** Use `runTest` with `StandardTestDispatcher` and manually advance with `advanceUntilIdle()` where needed.
 - **Flow Testing:** Use `Turbine` for asserting emitted events and state flows.
 - **Fixtures:** Follow production-app conventions; no centralized test-utils mandated yet.
 - **Mocks:** Prefer hand-rolled fakes over Koin test module overrides.
 - **Naming:** Test method naming is freeform.

---

## Security
- **Admin-only Actions:** Confirm only for deletes or risky multi-entity updates with `DialogType.CONFIRM_RISKY_OPERATION`.
- **Input Sanitization:** No extra rules currently.
 - **Clipboard:** No restrictions on copying sensitive text at this time.

## Resources & Assets
- **Strings:** For rapid development and English-only, avoid creating new string resources for general UI copy; prefer dynamic strings. Data error messages continue to use resources via `DataError.toUiText()`.
- **Colors:** Do not define new palette entries or ad-hoc colors in features; use existing design-system colors only which will be easiliy accessible with MaterialTheme.colorScheme.<color>.
 - **File Uploads (CSV):** Validate size and MIME type and show clear error messages.

## Data & Serialization
- **UUIDs:** Use validators in `core/presentation/validation` for all IDs.
- **Dates & Times:** Use `LocalDate`/`LocalTime` strictly in state and domain models; for UI display, use utilities from `DateTimeUtils.kt`.
- **Numbers:** Use localized decimal formatting in UI where applicable.