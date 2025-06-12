import SwiftUI
import ComposableArchitecture

struct AddNoteRoute: View {
	
	@Bindable var store: StoreOf<AddNoteFeature>
	
    var body: some View {
        AddNoteScreen()
    }
}
