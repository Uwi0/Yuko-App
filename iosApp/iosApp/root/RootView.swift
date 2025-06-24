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
				
			//MARK: Note Screen Feature
			case let .notesScreen(notesStore):
				NotesRoute(store: notesStore)
				
			case let .addNoteScreen(addNoteStore):
				AddNoteRoute(store: addNoteStore)
				
			case let .noteScreen(noteStore):
				NoteRoute(store: noteStore)
				
			case let .todosScreen(todosStore):
				TodosRoute(store: todosStore)
				
			case let .addTodoScreen(addTodoStore):
				AddTodoRoute(store: addTodoStore)
				
			case let .todoScreen(todoStore):
				TodoRoute(store: todoStore)
				
			case let .habitsScreen(habitsStore):
				HabitsRoute(store: habitsStore)
				
			case let .settingsScreen(settingsStore):
				SettingsRoute(store: settingsStore)
				
			}
		}
	}
}
