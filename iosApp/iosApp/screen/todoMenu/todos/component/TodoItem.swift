import SwiftUI
import Shared

struct TodoItem: View {
	
	let item: TodoModel
	var onToggle: (Int64, Bool) -> Void = {_, _ in }
	@Environment(\.horizontalSizeClass) var horizontalSizeClass
	
	private var isGridView: Bool {
		horizontalSizeClass == .regular
	}
	
	var body: some View {
		ContentView()
			.frame(
				maxWidth: isGridView ? SizeConstant.ItemMaxWidht : .infinity,
				minHeight: 90,
				maxHeight: 120,
				alignment: .leading
			)
			.background(ColorTheme.surface)
			.overlay(
				RoundedRectangle(cornerRadius: ShapeStyles.medium)
					.stroke(ColorTheme.onSurface, lineWidth: 1)
			)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		HStack(alignment: .center, spacing: 16) {
			CustomCheckbox()
			DetailContent()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 8)
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
		let tintColor = item.isDone ? ColorTheme.primary : ColorTheme.secondary
		Button(action: { onToggle(item.id, !item.isDone)} ) {
			Image(systemName: item.isDone ? "checkmark.circle" : "circle")
				.resizable()
				.frame(width: 24, height: 24)
				.foregroundColor(tintColor)
		}
		.buttonStyle(.plain)
	}
	
}

#Preview {
	VStack {
		TodoItem(item: .companion.default())
		TodoItem(item: TodoModel(id: 0, title: "Hello world", description: "World world", isDone: true))
	}
}
