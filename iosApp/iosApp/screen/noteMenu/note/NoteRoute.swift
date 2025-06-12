import SwiftUI
import ComposableArchitecture

struct NoteRoute: View {
	
	@Bindable var store: StoreOf<NoteFeature>
	
    var body: some View {
        NoteScreen()
    }
}
