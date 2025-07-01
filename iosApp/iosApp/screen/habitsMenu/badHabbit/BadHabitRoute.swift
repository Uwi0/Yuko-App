import SwiftUI
import ComposableArchitecture
import Shared

struct BadHabitRoute: View {
	
	@Bindable var store: StoreOf<BadHabitFeature>
	@StateObject private var viewModel = BadHabitViewModel()
	
	var body: some View {
		BadHabitScreen(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData(id: store.id)
			}
			.onReceive(viewModel.effectPublisher) { effect in
				observe(effect: effect)
			}
	}
	
	private func observe(effect: BadHabitEffect) {
		switch onEnum(of: effect) {
		case let .showError(error): print("Error \(error.message)")
		case .navigateBack: store.send(.navigateBack)
		}
	}
}
