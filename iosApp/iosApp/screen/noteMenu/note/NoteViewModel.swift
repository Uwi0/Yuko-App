import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class NoteViewModel: ObservableObject {
	
	@Published var state: NoteState = .companion.default()
	
	private let viewModel: NoteViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<NoteEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	var effectPublisher: AnyPublisher<NoteEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	func initData(noteId: Int64) {
		viewModel.doInitData(noteId: noteId)
		observeState()
		observeEffect()
	}
	
	func handle(event: NoteEvent) {
		viewModel.handleEvent(event: event)
	}
	
	func observeState() {
		let publihser = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] state in
			self?.update(state: state)
		}
	}
	
	private func update(state: NoteState) {
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
	
	private func send(effect: NoteEffect) {
		DispatchQueue.main.async {
			self.effectSubject.send(effect)
		}
	}
	
	deinit {
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
}
