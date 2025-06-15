import Foundation
import ComposableArchitecture

extension RootFeature {
	static func reducePomodoroNavigation(state: inout State, action: Action) -> Effect<Action> {
		switch action {
			
		case .mainMenu(.tapToPomodoro):
			state.path = StackState()
			state.path.append(.pomodoroScreen(PomodoroFeature.State()))
			return .none
			
		case .path(.element(_, action: .pomodoroScreen(.navigateToMain))):
			return .run { send in
				try await Task.sleep(nanoseconds: Self.delay)
				await send(.resetNavigation)
			}
			
		default: return .none
		}
	}
}
