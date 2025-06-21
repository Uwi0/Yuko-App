import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class AddTodoViewModel: ObservableObject {
	
	private let viewModel: AddTodoViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<AddTodoEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	@Published var state: AddTodoState = .companion.default()
	var effectPublsher: AnyPublisher<AddTodoEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	func initData() {
		observeEffect()
		observeState()
	}
	
	func handle(event: AddTodoEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("Compose \(completion)")
			} receiveValue: { [weak self] state in
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
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		effectCancellable?.cancel()
		stateCancellable?.cancel()
	}
}
