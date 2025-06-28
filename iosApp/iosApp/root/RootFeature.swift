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
		case resetNavigation
		case navigateBack
	}
	
	@Reducer(state: .equatable)
	enum Path {
		case pomodoroScreen(PomodoroFeature)
		
		//MARK: Notes feature
		case notesScreen(NotesFeature)
		case noteScreen(NoteFeature)
		case addNoteScreen(AddNoteFeature)
		
		//MARK: Todos Feature
		case todosScreen(TodosFeature)
		case addTodoScreen(AddTodoFeature)
		case todoScreen(TodoFeature)
		
		//MARK: Habit feature
		case habitsScreen(HabitsFeature)
		case settingsScreen(SettingsFeature)
	}
	
	static let delay: UInt64 = 300_000_000
	
	var body: some ReducerOf<Self> {
		Scope(state: \.mainMenu, action: \.mainMenu) {
			MainMenuFeature()
		}
		
		Reduce(Self.baseReducer(state:action:))
		Reduce(Self.reducePomodoroNavigation(state:action:))
		Reduce(Self.reduceNoteNavigation(state:action:))
		Reduce(Self.reduceTodosNavigation(state:action:))
		Reduce(Self.reduceHabitNavigation(state:action:))
		
		.forEach(\.path, action: \.path)
	}
	
	
}

extension RootFeature {
	
	static func baseReducer(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToSettings):
			state.path = StackState()
			state.path.append(.settingsScreen(SettingsFeature.State()))
			return .none
			
		case .path:
			return .none
			
		case .resetNavigation:
			state.path = StackState()
			return .none
			
		case .navigateBack:
			_ = state.path.popLast()
			return .none
			
		default: return .none
		}
	}
}
