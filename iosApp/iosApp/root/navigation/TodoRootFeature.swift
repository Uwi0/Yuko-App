import Foundation
import ComposableArchitecture

extension RootFeature {
	
	static func reduceTodosNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToTodos):
			state.path = StackState()
			state.path.append(.todosScreen(TodosFeature.State()))
			return .none
			
		case .path(.element(_, .todosScreen(.tapToAddTodo))):
			state.path.append(.addTodoScreen(AddTodoFeature.State()))
			return .none
			
		case .path(.element(_, .todosScreen(.navigateBack))):
			_ = state.path.popLast()
			return .none
			
		case .path(.element(_, .addTodoScreen(.navigateBack))):
			_ = state.path.popLast()
			return .none
			
		default : return .none
		}
	}
	
}
