import SwiftUI
import Shared

struct NoteItemView: View {
	
	let note: NotesModel
	@Environment(\.horizontalSizeClass) var horizontalSizeClass
	private var isGridView: Bool {
		horizontalSizeClass == .regular
	}
	
	var body: some View {
		VStack(spacing: 16) {
			Text(note.title)
				.font(Typography.titleMedium)
			Text(note.note)
				.font(Typography.bodyMedium)
		}
		.padding()
		.frame(
			maxWidth: isGridView ? SizeConstant.ItemMaxWidht : .infinity,
			alignment: .leading
		)
		.background(ColorTheme.surface)
		.overlay(
			RoundedRectangle(cornerRadius: ShapeStyles.medium)
				.stroke(ColorTheme.onSurface, lineWidth: SizeConstant.BorderWidth)
		)
		
	}
}

#Preview {
	NoteItemView(note: NotesModel(id: 0, title: "Hello", note: "World"))
}
