import Foundation
import ComposableArchitecture

@Reducer
struct PomodoroFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateToMain
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateToMain: return .none
			}
		}
	}
}
