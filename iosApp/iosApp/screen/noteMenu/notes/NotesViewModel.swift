import Foundation
import Combine
import Shared
import KMPNativeCoroutinesCombine

final class NotesViewModel: ObservableObject {
	
	@Published var state: NotesState = .companion.default()
	
	private let viewModel: NotesViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<NotesEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	var effectPublisher: AnyPublisher<NotesEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	func initData() {
		viewModel.doInitData()
		observeState()
		observeEffect()
	}
	
	func handle(event: NotesEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] state in
			self?.update(state: state)
		}
	}
	
	private func update(state: NotesState) {
		DispatchQueue.main.async {
			self.state = state
		}
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] effect in
			self?.send(effect: effect)
		}
	}
	
	func send(effect: NotesEffect) {
		DispatchQueue.main.async {
			self.effectSubject.send(effect)
		}
	}
	
	deinit {
		effectCancellable?.cancel()
	}
}
