import Foundation
import ComposableArchitecture

@Reducer
struct MainMenuFeature: Reducer {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case tapToPomodoro
		case tapToNotes
		case tapToTodos
		case tapToHabits
		case tapToSettings
	}
	
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .tapToPomodoro: return .none
			case .tapToNotes: return .none
			case .tapToTodos: return .none
			case .tapToHabits: return .none
			case .tapToSettings: return .none
			}
		}
	}
	
}
