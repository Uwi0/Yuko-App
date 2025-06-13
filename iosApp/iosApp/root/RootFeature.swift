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
		case habitsScreen(HabitsFeature)
		case settingsScreen(SettingsFeature)
	}
	
	var body: some ReducerOf<Self> {
		Scope(state: \.mainMenu, action: \.mainMenu) {
			MainMenuFeature()
		}
		
		Reduce(Self.baseReducer(state:action:))
		Reduce(Self.reducePomodoroNavigation(state:action:))
		Reduce(Self.reduceNoteNavigation(state:action:))
		
		.forEach(\.path, action: \.path)
	}
	
	private static let delay: UInt64 = 300_000_000
}

extension RootFeature {
	static func baseReducer(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
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
			
		case .resetNavigation:
			state.path = StackState()
			return .none
			
		default: return .none
		}
	}
}

extension RootFeature {
	static func reducePomodoroNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToPomodoro):
			state.path = StackState()
			state.path.append(.pomodoroScreen(PomodoroFeature.State()))
			return .none
			
		case .path(.element(_, action: .pomodoroScreen(.navigateToMain))):
			return .run { send in
				try await Task.sleep(nanoseconds: Self.delay)
				await send(.resetNavigation)
			}
			
		default: return .none
		}
	}
}

extension RootFeature {
	static func reduceNoteNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToNotes):
			state.path = StackState()
			state.path.append(.notesScreen(NotesFeature.State()))
			return .none
			
		case .path(.element(_, .notesScreen(.tapToAddNote))):
			state.path.append(.addNoteScreen(AddNoteFeature.State()))
			return .none
			
		case .path(.element(_, .notesScreen(.tapToNote))):
			state.path.append(.noteScreen(NoteFeature.State()))
			return .none
			
		default: return .none
		}
	}
}
