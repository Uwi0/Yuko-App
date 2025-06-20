import SwiftUI
import ComposableArchitecture
import Shared

struct TodosRoute: View {
	
	@Bindable var store: StoreOf<TodosFeature>
	@StateObject private var viewModel = TodosViewModel()
	
	var body: some View {
		TodosScreen(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}.onReceive(viewModel.effectPublihser){ effect in
				observe(effect: effect)
			}
	}
	
	func observe(effect: TodosEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case .tapToAddTodo: store.send(.tapToAddTodo)
		}
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
