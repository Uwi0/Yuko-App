import SwiftUI
import ComposableArchitecture

struct NotesRootView: View {
	
	@Bindable var store: StoreOf<NotesRootFeature>
	
	var body: some View {
		NavigationStack(path: $store.scope(state: \.path, action: \.path)) {
			NotesRoute(store: store.scope(state: \.notes, action: \.notes))
		} destination: { state in
			switch state.case {
			case let .addNoteScreen(addNoteStore):
				AddNoteRoute(store: addNoteStore)
			case let .noteScreen(noteStore):
				NoteRoute(store: noteStore)
			}
		}
	}
}
