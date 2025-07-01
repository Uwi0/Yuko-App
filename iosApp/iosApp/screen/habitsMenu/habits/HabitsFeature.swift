import Foundation
import ComposableArchitecture

@Reducer
struct HabitsFeature {
	@ObservableState
	struct State: Equatable {
		
	}
	
	enum Action {
		case navigateBack
		case tapToAddHabit
		case navigateToGoodHabit(Int64)
		case navigateToBadHabit(Int64)
	}
	
	var body: some ReducerOf<Self> {
		Reduce { state, action in
			switch action {
			case .navigateBack: return .none
			case .tapToAddHabit: return .none
			case .navigateToGoodHabit: return .none
			case .navigateToBadHabit: return .none
			}
		}
	}
}
