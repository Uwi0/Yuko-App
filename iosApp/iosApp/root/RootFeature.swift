import Foundation
import ComposableArchitecture

@Reducer
struct RootFeature {
	@ObservableState
	struct State {
		var path = StackState<Path.State>()
		var mainMenu = MainMenuFeature.State()
	}
	
	enum Action {
		case path(StackAction<Path.State, Path.Action>)
		case mainMenu(MainMenuFeature.Action)
	}
	
	@Reducer(state: .equatable)
	enum Path {
		case pomodoroScreen(PomodoroFeature)
		case notesScreen(NotesFeature)
		case todosScreen(TodosFeature)
		case habitsScreen(HabitsFeature)
		case settingsScreen(SettingsFeature)
	}
	
	var body: some ReducerOf<Self> {
		Scope(state: \.mainMenu, action: \.mainMenu) {
			MainMenuFeature()
		}
		Reduce { state, action in
			switch action {
				
			case .mainMenu(.tapToPomodoro):
				state.path = StackState()
				state.path.append(.pomodoroScreen(PomodoroFeature.State()))
				return .none
				
			case .mainMenu(.tapToNotes):
				state.path = StackState()
				state.path.append(.notesScreen(NotesFeature.State()))
				return .none
				
			case .mainMenu(.tapToTodos):
				state.path = StackState()
				state.path.append(.todosScreen(TodosFeature.State()))
				return .none
				
			case .mainMenu(.tapToHabits):
				state.path = StackState()
				state.path.append(.habitsScreen(HabitsFeature.State()))
				return .none
				
			case .mainMenu(.tapToSettings):
				state.path = StackState()
				state.path.append(.settingsScreen(SettingsFeature.State()))
				return .none
				
			case .path:
				return .none
			}
		}
		.forEach(\.path, action: \.path)
	}
}
