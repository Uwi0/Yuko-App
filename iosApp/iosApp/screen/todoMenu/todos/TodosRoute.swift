import SwiftUI
import ComposableArchitecture
import Shared

struct TodosRoute: View {
	
	@Bindable var store: StoreOf<TodosFeature>
	@StateObject private var viewModel = TodosViewModel()
	
	var body: some View {
		TodosScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
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
		case let .navigateToTodo(item): store.send(.navigateToTodo(item.id))
		case let .showError(error): print("error: \(error.message)")
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
