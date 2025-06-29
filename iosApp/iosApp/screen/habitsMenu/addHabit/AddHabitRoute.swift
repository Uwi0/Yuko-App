import SwiftUI
import Shared
import ComposableArchitecture

struct AddHabitRoute: View {
	
	@Bindable var store: StoreOf<AddHabitFeature>
	@StateObject private var viewModel = AddHabitViewModel()
	
	var body: some View {
		AddHabitScren(onEvent: viewModel.handle(event:))
			.navigationBarBackButtonHidden(true)
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublihser) { effect in
				observe(effect: effect)
			}
	}
	
	private func observe(effect: AddHabitEffect) {
		switch onEnum(of: effect) {
		case .navigateBack: store.send(.navigateBack)
		}
	}
}
