import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TodosViewModel: ObservableObject {
	
	private let viewModel: TodosViewModelKt = Koin.shared.get()
	private let effectSubeject = PassthroughSubject<TodosEffect, Never>()
	private var effectCancellable: AnyCancellable?
	
	var effectPublihser: AnyPublisher<TodosEffect, Never> {
		effectSubeject.eraseToAnyPublisher( )
	}
	
	func initData() {
		observeEffect()
	}
	
	func handle(event: TodosEvent) {
		viewModel.handleEvent(event: event)
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
		effectCancellable?.cancel()
	}
	
}
