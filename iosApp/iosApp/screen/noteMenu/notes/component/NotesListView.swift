import SwiftUI
import Shared

struct NotesListView: View {
	let notes: [NotesModel]
	let onEvent: (NotesEvent) -> Void
	
	var body: some View {
		AdaptiveListGridView(items: notes) { note in
			NoteItemView(note: note)
				.onTapGesture { onEvent(.TapToNote(noteId: note.noteId)) }
		}
	}
}

extension NotesModel: @retroactive Identifiable {
	public var id: Int64 { self.noteId }
}


#Preview {
	NotesListView(notes: dummyNotesModel, onEvent: { _ in })
}
