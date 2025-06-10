import SwiftUI
import ComposableArchitecture

struct TodosRoute: View {
	
	@Bindable var store: StoreOf<TodosFeature>
	
	var body: some View {
		TodosScreen()
	}
}

#Preview {
	TodosRoute(
		store: Store(
			initialState: TodosFeature.State(),
			reducer: { TodosFeature()}
		)
	)
}
