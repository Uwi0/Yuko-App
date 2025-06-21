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
	
	func initData(noteId: Int64) {
		viewModel.doInitData(noteId: noteId)
		observeState()
		observeEffect()
	}
	
	func handle(event: AddNoteEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCanellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		stateCancellable?.cancel( )
		effectCanellable?.cancel()
	}
}
