import Foundation
import ComposableArchitecture

@Reducer
struct AddHabitFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateBack
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			}
		}
	}
}
