import Foundation
import ComposableArchitecture

enum SessionStatus {
    case idle
    case breakTime
}

enum PomodoroMenuRoute: Equatable {
    case success
    case fail
}

struct PomodoroFeature: Reducer {
    struct State: Equatable {
        var route: PomodoroMenuRoute? = nil
        var status: SessionStatus = .idle
        var isPresented: Bool {
            route == .success || route == .fail
        }
    }
    
    enum Action: Equatable {
        case timerCompleted
        case timerFailed
        case routeDismissed
        case tapBreak
        case tabBackToMainMenu
    }
    
    func reduce(into state: inout State, action: Action) -> Effect<Action> {
        switch action {
        case .timerCompleted:
            state.route = .success
            state.status = .idle
            return .none
        case .timerFailed:
            state.route = .fail
            state.status = .idle
            return .none
        case .routeDismissed:
            state.route = nil
            return .none
        case .tapBreak:
            state.route = nil
            state.status = .breakTime
            return .none
        case .tabBackToMainMenu:
            return .none
        }
    }
}
