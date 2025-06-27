import SwiftUI
import Shared
import ComposableArchitecture

struct AddTodoRoute: View {
	
	@Bindable var store: StoreOf<AddTodoFeature>
	@StateObject private var viewModel = AddTodoViewModel()
	
	var body: some View {
		AddTodoScreen(state: $viewModel.state, onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData(id: store.todoId)
			}
			.onReceive(viewModel.effectPublsher) { effect in
					observe(effect: effect)
			}
	}
	
	private func observe(effect: AddTodoEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case let .showError(error): print("Error: \(error.message)")
		}
	}
}
