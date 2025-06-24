import SwiftUI
import Shared

struct TodosListView: View {
	
	let todos: [TodoModel]
	let onEvent: (TodosEvent) -> Void
	
	@Environment(\.horizontalSizeClass) var horizontalSizeClass
	private var isGrid: Bool {
		horizontalSizeClass == .regular
	}
	
	private var gridSize: [GridItem] {
		[GridItem(.adaptive(minimum: 200), spacing: 16)]
	}
	
	var body: some View {
		ScrollView {
			ContentView()
		}
		.scrollIndicators(.hidden)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		if isGrid {
			LazyVGrid(columns: gridSize, spacing: 16) {
				ListView()
			}
		} else {
			LazyVStack(spacing: 16) {
				ListView()
			}
		}
	}
	
	@ViewBuilder
	private func ListView() -> some View {
		ForEach(todos, id: \.id) { item in
			TodoItem(
				item: item,
				onToggle: { id, isDone in
					onEvent(.ToggleTodoIsDone(id: id, isDone: isDone))
				}
			)
			.onTapGesture {
				onEvent(.NavigateToTodo(id: item.id))
			}
		}
	}
}

#Preview {
	TodosListView(todos: dummyTodosModel, onEvent: { _ in })
}
