import Foundation
import ComposableArchitecture

@Reducer
struct PomodoroFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateToSuccess
		case navigateToFailure
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateToSuccess:
				return .none
			case .navigateToFailure:
				return .none
			}
		}
	}
}
