import SwiftUI
import Shared
import ComposableArchitecture

struct AddTodoRoute: View {
	
	@Bindable var store: StoreOf<AddTodoFeature>
	
	var body: some View {
		AddTodoScreen()
	}
}
