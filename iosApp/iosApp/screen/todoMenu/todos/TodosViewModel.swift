import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TodosViewModel: ObservableObject {
	
	@Published var state: TodosState = .companion.default()
	var effectPublihser: AnyPublisher<TodosEffect, Never> {
		effectSubeject.eraseToAnyPublisher( )
	}
	
	private let viewModel: TodosViewModelKt = Koin.shared.get()
	private let effectSubeject = PassthroughSubject<TodosEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		viewModel.doInitData()
		observeEffect()
		observeState()
	}
	
	func handle(event: TodosEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publihser = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				self?.effectSubeject.send(effect)
			}
	}
	
	deinit {
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
	
}
