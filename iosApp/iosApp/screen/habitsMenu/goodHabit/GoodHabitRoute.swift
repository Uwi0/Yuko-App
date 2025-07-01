import SwiftUI
import ComposableArchitecture
import Shared

struct GoodHabitRoute: View {
	
	@Bindable var store: StoreOf<GoodHabitFeature>
	@StateObject private var viewModel = GoodHabitViewModel()
	
	var body: some View {
		GoodHabitScreen(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData(id: store.id)
			}
			.onReceive(viewModel.efectPublihser) { effect in
				observe(effect: effect)
			}
	}
	
	private func observe(effect: GoodHabitEffect) {
		switch onEnum(of: effect) {
		case let .showError(error): print("error \(error.message)")
		case .navigateBack: store.send(.navigateBack)
		}
	}
}
