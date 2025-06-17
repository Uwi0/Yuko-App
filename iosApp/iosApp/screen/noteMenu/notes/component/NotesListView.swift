import SwiftUI
import Shared

struct NotesListView: View {
	@Environment(\.horizontalSizeClass) var horizontalSizeClass
	let notes: [NotesModel]
	let onEvent: (NotesEvent) -> Void
	
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
		ForEach(notes, id: \.id) { note in
			NoteItemView(note: note)
				.onTapGesture { onEvent(.TapToNote(noteId: note.id)) }
		}
	}
}

#Preview {
	NotesListView(notes: dummyNotesModel, onEvent: { _ in })
}
