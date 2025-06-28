import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TodoViewModel: ObservableObject {
	
	@Published var state: TodoState = .companion.default()
	var effectPublihser: AnyPublisher<TodoEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: TodoViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<TodoEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData(id: Int64) {
		viewModel.doInitData(id: id)
		observeEffect()
		observeState()
	}
	
	func handle(event: TodoEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publisher = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				guard let effect else { return }
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		viewModel.onCleared()
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
}
