import SwiftUI
import Shared
import ComposableArchitecture

struct AddTodoRoute: View {
	
	@Bindable var store: StoreOf<AddTodoFeature>
	@StateObject private var viewModel = AddTodoViewModel()
	
	var body: some View {
		AddTodoScreen(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublsher) { effect in
					observe(effect: effect)
			}
	}
	
	private func observe(effect: AddTodoEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		}
	}
}
