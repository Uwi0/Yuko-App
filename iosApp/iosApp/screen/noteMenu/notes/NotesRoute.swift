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
			.onReceive(viewModel.$effect.compactMap { $0 }) { effect in
					observeEffect(effect: effect)
			}
	}
	
	private func observeEffect(effect: NotesEffect?) {
		guard let effect = effect else { return }
		switch onEnum(of: effect) {
		case .tapToNote: store.send(.tapToNote)
		case .tapToAddNote: store.send(.tapToAddNote)
		}
		
		viewModel.effect = nil
	}
}
