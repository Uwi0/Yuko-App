import Foundation
import Combine
import KMPNativeCoroutinesCombine
import Shared

final class AddNoteViewModel: ObservableObject {
	
	@Published var state: AddNoteState = AddNoteState.companion.default()
	
	private let viewModel: AddNoteViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<AddNoteEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCanellable: AnyCancellable?
	
	var effectPublisher: AnyPublisher<AddNoteEffect, Never> {
		effectSubject.eraseToAnyPublisher( )
	}
	
	func initData() {
		observeState()
		observeEffect()
	}
	
	func handle(event: AddNoteEvent) {
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
	
	private func update(state: AddNoteState) {
		DispatchQueue.main.async {
			self.state = state
		}
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
		stateCancellable?.cancel( )
		effectCanellable?.cancel()
	}
}
