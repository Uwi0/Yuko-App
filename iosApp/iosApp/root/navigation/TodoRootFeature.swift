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
			return .run { send in await send(.navigateBack) }
			
		case .path(.element(_, .todosScreen(.navigateToTodo(let id)))):
			state.path.append(.todoScreen(TodoFeature.State(todoId: id)))
			return .none
			
		case .path(.element(_, .addTodoScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		case .path(.element(_, .todoScreen(.navigateBack))):
			return .run { send in await send(.navigateBack) }
			
		case .path(.element(_, .todoScreen(.tapToEditTodo(let id)))):
			state.path.append(.addTodoScreen(AddTodoFeature.State(todoId: id)))
			return .none
			
		default : return .none
		}
	}
	
}
