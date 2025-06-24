import SwiftUI
import Shared
import ComposableArchitecture

struct TodoRoute: View {
	
	@Bindable var store: StoreOf<TodoFeature>
	@StateObject private var viewModel = TodoViewModel()
	
	var body: some View {
		TodoScreen(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublihser) { effect in
				observe(effect: effect)
			}
	}
	
	private func observe(effect: TodoEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		case let .showError(error): print("error: \(error)")
		}
	}
}
