import Foundation
import Combine
import Shared
import KMPNativeCoroutinesCombine

final class NotesViewModel: ObservableObject {
	
	private let viewModel: NotesViewModelKt = Koin.shared.get()
	private var effectCancellable: AnyCancellable?
	private let effectSubject = PassthroughSubject<NotesEffect, Never>()
	
	var effectPublisher: AnyPublisher<NotesEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
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
			self?.effectSubject.send(effect)
		}
	}
	
	deinit {
		effectCancellable?.cancel()
	}
}
