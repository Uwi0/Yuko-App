import SwiftUI
import Shared

struct NoteItemView: View {
	
	let note: NotesModel
	
	var body: some View {
		VStack(alignment: .leading, spacing: 16) {
			Text(note.title)
				.font(Typography.titleMedium)
			Text(note.note)
				.font(Typography.bodyMedium)
		}
		.modifier(BorderedCardModifier())
	}
}

#Preview {
	NoteItemView(note: NotesModel(noteId: 0, title: "Hello", note: "World"))
}
