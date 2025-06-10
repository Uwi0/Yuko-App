import SwiftUI
import ComposableArchitecture

struct NotesRoute: View {
	
	@Bindable var store: StoreOf<NotesFeature>
	
	var body: some View {
		NotesScreen()
	}
}

#Preview {
	NotesRoute(
		store: Store(
			initialState: NotesFeature.State(),
			reducer: { NotesFeature() }
		)
	)
}
