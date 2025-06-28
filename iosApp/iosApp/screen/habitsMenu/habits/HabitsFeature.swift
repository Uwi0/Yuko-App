import Foundation
import ComposableArchitecture

@Reducer
struct HabitsFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateBack
		case tapToAddHabbit
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			case .tapToAddHabbit: return .none
			}
		}
	}
}
