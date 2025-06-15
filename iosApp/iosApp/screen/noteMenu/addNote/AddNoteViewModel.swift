import Foundation
import Combine
import KMPNativeCoroutinesCombine
import Shared

final class AddNoteViewModel: ObservableObject {
	
	private let viewModel: AddNoteViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<AddNoteEffect, Never>()
	private var effectCanellable: AnyCancellable?
	
	var effectPublisher: AnyPublisher<AddNoteEffect, Never> {
		effectSubject.eraseToAnyPublisher( )
	}
	
	func initData() {
		observeEffect()
	}
	
	func handle(event: AddNoteEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCanellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] effect in
			self?.send(effect: effect)
		}
	}
	
	func send(effect: AddNoteEffect) {
		DispatchQueue.main.async {
			self.effectSubject.send(effect)
		}
	}
	
	deinit {
		effectCanellable?.cancel()
	}
}
