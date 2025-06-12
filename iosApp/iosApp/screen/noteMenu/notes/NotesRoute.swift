import SwiftUI
import Shared
import ComposableArchitecture

struct NotesRoute: View {
	
	@Bindable var store: StoreOf<NotesFeature>
	@StateObject private var viewModel = NotesViewModel()
	
	var body: some View {
		NotesScreen(onEvent: viewModel.handle(event:))
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublisher) { effect in
				observeEffect(effect: effect)
			}
	}
	
	private func observeEffect(effect: NotesEffect) {
		switch onEnum(of: effect) {
		case .tapToNote: store.send(.tapToNote)
		case .tapToAddNote: store.send(.tapToAddNote)
		}
	}
}
