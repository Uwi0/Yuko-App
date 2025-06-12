import SwiftUI
import ComposableArchitecture

struct RootView: View {
	
	@Bindable var store: StoreOf<RootFeature>
	
	var body: some View {
		NavigationStack(path: $store.scope(state: \.path, action: \.path)) {
			MainMenuScreen(store: store.scope(state: \.mainMenu, action: \.mainMenu))
		} destination: { state in
			switch state.case {
			case let .pomodoroScreen(pomodoroStore):
				PomodoroRoute(store: pomodoroStore)
			case let .notesScreen(notesStore):
				NotesRoute(store: notesStore)
			case let .todosScreen(todosStore):
				TodosRoute(store: todosStore)
			case let .habitsScreen(habitsStore):
				HabitsRoute(store: habitsStore)
			case let .settingsScreen(settingsStore):
				SettingsRoute(store: settingsStore)
			}
		}
	}
}
