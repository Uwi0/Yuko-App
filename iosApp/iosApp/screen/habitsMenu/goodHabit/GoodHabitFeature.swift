import Foundation
import ComposableArchitecture

@Reducer
struct GoodHabitFeature {
	@ObservableState
	struct State: Equatable {
		var id: Int64 = 0
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
