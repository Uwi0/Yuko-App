import SwiftUI
import Shared

struct TodoItem: View {
	
	let item: TodoModel
	var onToggle: (Int64, Bool) -> Void = {_, _ in }
	
	var body: some View {
		ContentView()
			.modifier(BorderedCardModifier())
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		HStack(alignment: .center, spacing: 16) {
			CustomCheckbox()
			DetailContent()
		}
	}
	
	@ViewBuilder
	private func DetailContent() -> some View {
		VStack(alignment: .leading, spacing: 16) {
			Text(item.title)
				.font(Typography.titleMedium)
				.bold()
			Text(item.description_)
				.font(Typography.bodyMedium)
				.foregroundStyle(ColorTheme.outline)
		}
	}
	
	@ViewBuilder
	private func CustomCheckbox() -> some View {
		CustomCheckBox(
			isSelected: Binding(
				get: { item.isDone },
				set: { isDone in onToggle(item.id, isDone)}
			)
		)
	}
	
}

#Preview {
	VStack {
		TodoItem(item: .companion.default())
		TodoItem(item: TodoModel(todoId: 0, title: "Hello world", description: "World world", isDone: true))
	}
}
