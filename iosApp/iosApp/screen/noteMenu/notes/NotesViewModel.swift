import Foundation
import Combine
import Shared
import KMPNativeCoroutinesCombine

final class NotesViewModel: ObservableObject {
	@Published var effect: NotesEffect? = nil
	
	private let viewModel: NotesViewModelKt = Koin.shared.get()
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		observeEffect()
	}
	
	func handle(event: NotesEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] effect in
			self?.update(effect: effect)
		}
	}
	
	private func update(effect: NotesEffect) {
		DispatchQueue.main.async {
			self.effect = effect
		}
	}
}
