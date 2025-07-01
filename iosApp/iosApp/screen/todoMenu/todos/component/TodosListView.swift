import SwiftUI
import Shared

struct TodosListView: View {
	
	let todos: [TodoModel]
	let onEvent: (TodosEvent) -> Void
	
	var body: some View {
		AdaptiveListGridView(items: todos) { todo in
			TodoItem(
				item: todo,
				onToggle: { id, isDone in
					onEvent(.ToggleTodoIsDone(id: id, isDone: isDone))
				}
			)
			.onTapGesture {
				onEvent(.NavigateToTodo(id: todo.todoId))
			}
		}
	}
	
}

extension TodoModel: @retroactive Identifiable {
	public var id: Int64 { self.todoId }
}

#Preview {
	TodosListView(todos: dummyTodosModel, onEvent: { _ in })
}
