import Foundation
import ComposableArchitecture

extension RootFeature {
	static func reduceHabitNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToHabits):
			state.path = StackState()
			state.path.append(.habitsScreen(HabitsFeature.State()))
			return .none
			
		case .path(.element(_, .habitsScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		default : return .none
		}
	}
}
