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
			
		case .path(.element(_, .habitsScreen(.navigateToGoodHabit(let id)))):
			state.path.append(.goodHabitScreen(GoodHabitFeature.State(id: id)))
			return .none
			
		case .path(.element(_, .habitsScreen(.navigateToBadHabit(let id)))):
			state.path.append(.badHabitScreen(BadHabitFeature.State(id: id)))
			return .none
			
		case .path(.element(_, .habitsScreen(.tapToAddHabit))):
			state.path.append(.addHabitScreen(AddHabitFeature.State()))
			return .none
			
		case .path(.element(_, .addHabitScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		case .path(.element(_, .goodHabitScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		case .path(.element(_, .badHabitScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		default : return .none
		}
	}
}
